package com.sismics.books.util.form.validator;

import android.content.Context;

import com.sismics.books.R;

/**
 * Text presence validator.
 * 
 * @author bgamard
 */
public class Required implements ValidatorType {

    @Override
    public boolean validate(String text) {
        return text.trim().length() != 0;
    }

    @Override
    public String getErrorMessage(Context context) {
        return context.getString(R.string.validate_error_required);
    }

}
