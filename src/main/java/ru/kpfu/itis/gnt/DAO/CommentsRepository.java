package ru.kpfu.itis.gnt.DAO;

import ru.kpfu.itis.gnt.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository {

    Optional<List<Comment>> findAllComments(int postId);

    boolean addComment(Comment comment);

    boolean deleteComment(Comment comment);
}
