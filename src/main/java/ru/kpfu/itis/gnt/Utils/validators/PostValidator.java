package ru.kpfu.itis.gnt.Utils.validators;

import ru.kpfu.itis.gnt.entities.Post;

public class PostValidator {
    public static boolean areFieldsValid(Post post) {
        return (post.getTitle().length() > 3 && post.getBody().length() > 10);
    }
}
