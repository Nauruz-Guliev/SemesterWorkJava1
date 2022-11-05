package ru.kpfu.itis.gnt.DAO;

import ru.kpfu.itis.gnt.entities.Comment;

import java.util.List;
import java.util.Optional;

public class CommentsRepositoryImpl implements CommentsRepository {


    @Override
    public Optional<List<Comment>> findAllComments(int postId) {
        return Optional.empty();
    }

    @Override
    public boolean addComment(Comment comment) {
        return false;
    }

    @Override
    public boolean deleteComment(Comment comment) {
        return false;
    }
}
