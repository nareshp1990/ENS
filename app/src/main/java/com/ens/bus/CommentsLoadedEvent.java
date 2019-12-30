package com.ens.bus;

import com.ens.model.api.PagedResponse;
import com.ens.model.news.comment.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsLoadedEvent {

    private PagedResponse<Comment> comments;

}
