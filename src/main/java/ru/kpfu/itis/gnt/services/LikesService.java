package ru.kpfu.itis.gnt.services;


import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.exceptions.EmptyResultDbException;

public interface LikesService {

    void likeComment(int comment_id, int user_id) throws DBException;

    boolean likePost(int post_id, int user_id);

    int countPostLikes(int post_id) throws EmptyResultDbException;
}
