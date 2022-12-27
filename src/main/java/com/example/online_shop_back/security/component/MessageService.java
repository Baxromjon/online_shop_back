package com.example.online_shop_back.security.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageService {
    private static MessageSource messageSource;

    @Autowired
    public void setSomeThing(MessageSource messageSource) {
        MessageService.messageSource = messageSource;
    }

    public static String getMessage(String key) {
        try {
            return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return key;
        }
    }

    private static String merge(String action, String sourceKey) {
        return String.format(getMessage(action), getMessage(sourceKey));
    }


    /*==================SUCCESS===================*/

    public static String successEdit(String sourceKey) {
        return merge("SUCCESS_EDIT", sourceKey);
    }

    public static String successSave(String sourceKey) {
        return merge("SUCCESS_SAVE", sourceKey);
    }

    public static String successDelete(String sourceKey) {
        return merge("SUCCESS_DELETE", sourceKey);
    }


    /*===================ERROR===================*/

    public static String cannotDelete(String sourceKey) {
        return merge("CANNOT_DELETE", sourceKey);
    }

    public static String notFound(String sourceKey) {
        return merge("NOT_FOUND", sourceKey);
    }

    public static String alreadyExists(String sourceKey) {
        return merge("ALREADY_EXISTS", sourceKey);
    }

}
