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
    private boolean haveBase = false;
    private boolean firstExecute = true;
    private String config;
    private String url;
    private String user;
    private String password;

    public ParseJob(String config) {
        this.config = config.substring(0, config.indexOf('.'));
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            initConnection();
            parse();
        } catch (Exception e) {
            LOG.error("Exception");
            e.printStackTrace();
        }
    }

    private void checkDB(String user, String password) throws SQLException {
        String url = String.format("jdbc:postgresql://localhost/?user=%s&password=%s", user, password);
        try (Connection conn = DriverManager.getConnection(url)) {
            String sql = "SELECT EXISTS(SELECT datname FROM pg_catalog.pg_database WHERE datname = 'vacancies_db');";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            haveBase = rs.getBoolean(1);
            rs.close();
            if (!haveBase) {
                LOG.info("DB doesn't exist. Creating new DB...");
                st.executeUpdate("CREATE database vacancies_db;");
                haveBase = true;
            } else {
                LOG.info("DB exist. FirstExecute = false");
                firstExecute = false;
            }
        }
    }

    private void initConnection() throws SQLException {
        LOG.info("Start init() method...");
        ResourceBundle resource = ResourceBundle.getBundle(config);
        String driver = resource.getString("driver");
        this.user = resource.getString("user");
        this.password = resource.getString("password");
        this.url = resource.getString("url");

        checkDB(user, password);
        try {
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            LOG.error("Exception");
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS vacancies(id serial PRIMARY KEY, description text,"
                    + " create_date date, UNIQUE(description, create_date))");
        }
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
        Elements vacanciesEl = doc.select("td.postslisttopic").select("a:contains(java)");
        Elements datesEl = doc.select("td.altCol").select("[style=text-align:center]");
        String[] vacancies = clip(vacanciesEl);
        String[] datesStr = clip(datesEl);
        Date[] dates = formatDate(datesStr);

        for (int i = 0; i < vacancies.length; i++) {
            if (!vacancies[i].toLowerCase().contains("javascript")
                    || !vacancies[i].toLowerCase().contains("java script")) {
                paste(vacancies[i], dates[i]);
            }
        }
    }

    private void paste(String text, Date createDate) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
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
    }

    private String[] clip(Elements elements) {
        String[] array = new String[elements.size()];
        int i = 0;
        for (Element el : elements) {
            String str = el.toString();
            int start = str.indexOf('>') + 1;
            int stop = str.lastIndexOf('<');
            array[i] = (str.substring(start, stop));
            i++;
        }
        return array;
    }

    private Date[] formatDate(String[] dates) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yy");
        Date[] newDates = new Date[dates.length];
        for (int i = 0; i < dates.length; i++) {
            dates[i] = dates[i].substring(0, dates[i].length() - 7);
            if (dates[i].contains("сегод")) {
                dates[i] = dateFormat.format(new Date(System.currentTimeMillis()));
            } else if (dates[i].contains("вчера")) {
                dates[i] = dateFormat.format(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
            }
            java.util.Date dateUtil = dateFormat.parse(dates[i]);
            Date dateSql = new Date(dateUtil.getTime());
            newDates[i] = dateSql;
        }
        return newDates;
    }

    public static void main(String[] args) {
        String config = "app";
        if (args.length == 2) {
            config = args[1];
        }
        ResourceBundle resource = ResourceBundle.getBundle("app");
        String cron = resource.getString("cron_expression");
        HashMap<String, String> map = new HashMap<>();
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
