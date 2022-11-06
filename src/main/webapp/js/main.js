function addComment(postId, comment) {

    return fetch('http://localhost:8888/comment?postIndex=' + postId + '&comment=' + comment)
        .then((response) => {
            return response.json();
        }).then((comment) => {
            addCommentCard(comment);
        })

}

function addCommentCard(comment) {
    debugger;
    let newCommentCard =
        '<div class=\"card\">' +
        '<div class="card-body p-2 m-3">' +
        '<p>' + comment.text + '</p>' +
        '<div class="d-flex justify-content-between p-2">' +
        '<div class="d-flex flex-row">' +
        '<p class="small mb-0 ms-2">' + comment.authorName
        + '</p>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';

    document.getElementById('commentDiv').innerHTML = newCommentCard + document.getElementById('commentDiv').innerHTML;


}