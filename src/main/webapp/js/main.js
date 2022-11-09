function addLike(postId) {
    return fetch('http://localhost:8888/article/like?postIndex=' + postId)
        .then((response) => {
            return response.json();
        }).then((likeCount) => {
            setLikeCount(likeCount);
        })
}

function addComment(postId, comment) {
    document.getElementById('comment').value = "";
    if (comment !== "") {
        return fetch('http://localhost:8888/comment?postIndex=' + postId + '&comment=' + comment)
            .then((response) => {
                return response.json();
            }).then((comment) => {
                addCommentCard(comment);
            })
    }
}

function setLikeCount(like) {
    document.getElementById('likeIcon').innerText = like;
}

function addCommentCard(comment) {
    let newCommentCard =
        '<div class="card mb-3">' +
        '<div class="card-body">' +
        '<div class="d-flex flex-start">' +
        '<div class="w-100">' +
        '<div class="d-flex justify-content-between align-items-center mb-3">' +
        '<h6 class="text-primary fw-bold mb-0">' +
        comment.authorName +
        '</h6>' +
        '<p class="mb-0">' + comment.created_at + '</p>' +
        '</div>' +
        '<span class="text-dark ms-2 m-2">' + comment.text + '</span>' +
        '<div class="d-flex justify-content-between align-items-center">' +
        '<p class="small mb-0" style="color: #aaa;"></p>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';
    document.getElementById('commentDiv').innerHTML = newCommentCard + document.getElementById('commentDiv').innerHTML;
}