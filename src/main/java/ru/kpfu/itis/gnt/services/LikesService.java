package ru.kpfu.itis.gnt.services;



public interface LikesService {

    boolean likeComment(int comment_id, int user_id);

    boolean likePost(int post_id, int user_id);

    int countPostLikes(int post_id);
}
