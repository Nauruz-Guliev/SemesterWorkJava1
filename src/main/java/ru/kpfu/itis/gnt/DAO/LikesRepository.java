package ru.kpfu.itis.gnt.DAO;

import ru.kpfu.itis.gnt.exceptions.EmptyResultDbException;

public interface LikesRepository {
    boolean likePost(int user_id, int post_id);
    boolean likeComment(int user_id, int comment_id);

    boolean findCommentLike(int user_id, int comment_id) throws NullPointerException;
    boolean isPostLikePresent(int user_id, int post_id);

    boolean deleteCommentLike(int user_id, int comment_id) throws NullPointerException;
    boolean deletePostLike(int user_id, int post_id);

    int countCommentLike(int comment_id) throws EmptyResultDbException;
    int countPostLike(int post_id) throws EmptyResultDbException;
}
