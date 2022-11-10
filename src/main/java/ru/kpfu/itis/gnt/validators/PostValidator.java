package ru.kpfu.itis.gnt.validators;

import ru.kpfu.itis.gnt.entities.Post;

public class PostValidator {

    public static boolean areFieldsValid(Post post) {
        return (post.getTitle().length() > 3 && post.getTitle().length() > 10);
    }
}
