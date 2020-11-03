package de.dicecraft.dicemobmanager.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriorityEntryTest {

    @Test
    void testPriority() {
        final PriorityEntry<Object> priorityEntry = new PriorityEntry<>(1, new Object());
        assertThat(priorityEntry.getPriority()).isEqualTo(1);

        priorityEntry.setPriority(2);
        assertThat(priorityEntry.getPriority()).isEqualTo(2);
    }

    @Test
    void testEntry() {
        final Object entry = new Object();
        final PriorityEntry<Object> priorityEntry = new PriorityEntry<>(1, entry);
        assertThat(priorityEntry.getEntry()).isEqualTo(entry);
    }

    @Test
    void testToString() {
        final PriorityEntry<Object> priorityEntry = new PriorityEntry<>(1, new Object());
        assertThat(priorityEntry.toString().isEmpty()).isFalse();
    }
}