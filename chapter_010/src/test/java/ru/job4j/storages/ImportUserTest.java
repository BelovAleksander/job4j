package ru.job4j.storages;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImportUserTest {
    @Test
    public void whenAddUserThenShouldStorageNotEmpty() {
        ImportUser test = new ImportUser(new MemoryStorage());
        test.addUser(new User("Alex"));
        assertFalse(test.isEmpty());
    }
}