package ru.kpfu.itis.gnt.services;

import ru.kpfu.itis.gnt.entities.Comment;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;

import java.util.List;

public interface CommentsService {

    List<Comment> getAllComments(Post post) throws DBException;

    boolean addComment(Comment comment);


}
