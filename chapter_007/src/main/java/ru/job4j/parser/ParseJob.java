package ru.job4j.parser;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.postgresql.util.PSQLException;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ParseJob implements Job {
    private static final Logger LOG = Logger.getLogger(ParseJob.class);
    private Connection conn = null;
    private boolean haveBase = false;
    private boolean firstExecute = true;
    private String config;

    public ParseJob(String config) {
        this.config = config.substring(0, config.indexOf('.'));
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            init();
            parse();
        } catch (SQLException e) {
            LOG.error("SQL Exception");
            e.printStackTrace();
        } catch (ParseException e) {
            LOG.error("Parse Exception");
            e.printStackTrace();
        } catch (IOException e) {
            LOG.error("IO Exception");
            e.printStackTrace();
        }
    }

    private void init() throws SQLException {
        LOG.info("Start init() method...");
        ResourceBundle resource = ResourceBundle.getBundle(config);
        String driver = resource.getString("driver");
        String user = resource.getString("user");
        String password = resource.getString("password");
        String url = resource.getString("url");
        String urlForFirstExecute = String.format("jdbc:postgresql://localhost/?user=%s&password=%s", user, password);

        conn = DriverManager.getConnection(urlForFirstExecute);
        String sql = "SELECT EXISTS(SELECT datname FROM pg_catalog.pg_database WHERE datname = 'vacancies_db');";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        rs.next();
        haveBase = rs.getBoolean(1);
        rs.close();

        if (!haveBase) {
            LOG.info("DB doesn't exist. Creating new DB...");
            sql = "CREATE database vacancies_db;";
            st.executeUpdate(sql);
            haveBase = true;
        } else {
            LOG.info("DB exist. firstExecute = false");
            firstExecute = false;
        }
        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException e) {
            LOG.error("Instantiation Exception");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LOG.error("Illegal Access Exception");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            LOG.error("Class Not Found");
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(url, user, password);
        st = conn.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS vacancies(id serial PRIMARY KEY, description text, create_date date, UNIQUE(description, create_date))";
        st.executeUpdate(sql);
        st.close();
        LOG.info("Ending init() method...");
    }

    private void parse() throws IOException, ParseException, SQLException {
        LOG.info("Starting parse...");
        Document doc;
        if (firstExecute) {
            LOG.info("This is the first execution");
            doc = Jsoup.connect("http://www.sql.ru/forum/actualsearch.aspx?search="
                    + "java+%F0%E0%E7%F0%E0%E1%EE%F2%F7%E8%EA&sin=1&st=t&bid=0&a=&ma=0&dt=356&s=1&so=1").get();
        } else {
            doc = Jsoup.connect("http://www.sql.ru/forum/actualsearch.aspx?search="
                    + "java+%F0%E0%E7%F0%E0%E1%EE%F2%F7%E8%EA&sin=1&st=t&bid=0&a=&ma=0&dt=1&s=1&so=1").get();
        }
        Elements descsEl = doc.select("td.postslisttopic").select("a:contains(java)");
        Elements datesEl = doc.select("td.altCol").select("[style=text-align:center]");
        String[] descs = convert(descsEl);
        String[] dates = convert(datesEl);
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yy");
        for (int i = 0; i < dates.length; i++) {
            dates[i] = dates[i].substring(0, dates[i].length() - 7);
            if (dates[i].contains("сегод")) {
                dates[i] = dateFormat.format(new Date(System.currentTimeMillis()));
            } else if (dates[i].contains("вчера")) {
                dates[i] = dateFormat.format(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
            }
        }
        for (int i = 0; i < descs.length; i++) {
            if (!descs[i].toLowerCase().contains("javascript")
                    || !descs[i].toLowerCase().contains("java script")) {
                java.util.Date dateUtil = dateFormat.parse(dates[i]);
                Date dateSql = new Date(dateUtil.getTime());
                paste(descs[i], dateSql);
            }
        }
        conn.close();
    }

    private void paste(String text, Date createDate) throws SQLException {
        String sql = "INSERT INTO vacancies(description, create_date) VALUES(?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, text);
        ps.setDate(2, createDate);
        try {
            ps.execute();
            LOG.info("vacancy pasted");
        } catch (PSQLException e) {
            LOG.error("Duplicate vacancy");
        }
        ps.close();
    }

    private String[] convert(Elements elements) {
        String[] array = new String[elements.size()];
        int i = 0;
        for (Element el : elements) {
            String string = el.toString();
            int start = string.indexOf('>') + 1;
            int stop = string.lastIndexOf('<');
            array[i] = (string.substring(start, stop));
            i++;
        }
        return array;
    }

    public static void main(String[] args) {
        String config = "app";
        if (args.length == 2) {
            config = args[1];
        }
        ResourceBundle resource = ResourceBundle.getBundle("app");
        String cron = resource.getString("cron_expression");
        HashMap<String, String> map = new HashMap();
        map.put("Data#1", config);
        JobDetail parseJob = JobBuilder.newJob(ParseJob.class).withIdentity("parseJob", "group1")
                .setJobData(new JobDataMap(map)).build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(parseJob, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
