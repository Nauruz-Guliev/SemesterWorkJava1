package ru.kpfu.itis.gnt.Utils;

import ru.kpfu.itis.gnt.entities.Comment;

import java.time.LocalDate;
import java.util.Comparator;

public class CommentDateComparator implements Comparator<Comment> {
    @Override
    public int compare(Comment o1, Comment o2) {
       return LocalDate.parse(o1.getCreated_at()).compareTo(LocalDate.parse(o2.getCreated_at()));
    }
}
