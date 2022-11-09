package ru.kpfu.itis.gnt.DAO;

public interface LikesRepository {
    boolean likePost(int user_id, int post_id);
    boolean likeComment(int user_id, int comment_id);

    boolean findCommentLike(int user_id, int comment_id);
    boolean findPostLike(int user_id, int post_id);

    boolean deleteCommentLike(int user_id, int comment_id);
    boolean deletePostLike(int user_id, int post_id);

    int countCommentLike(int comment_id);
    int countPostLike(int post_id);
}
