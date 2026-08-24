package com.expensetracker.validation;

import jakarta.validation.GroupSequence;

@GroupSequence({
        ValidationGroups.Required.class,
        ValidationGroups.Size.class,
        ValidationGroups.Range.class,
        ValidationGroups.Format.class
})
public interface ValidationSequence {
}
