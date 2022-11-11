function addLike(postId) {
    return fetch('http://localhost:8888/post/like?postIndex=' + postId)
        .then((response) => {
            return response.json();
        }).then((likeCount) => {
            setLikeCount(likeCount);
        })
}

function setLikeCount(like) {
    let btnIcon = document.getElementById('likeIcon');
    if (like > parseInt(btnIcon.innerText)) {
        btnIcon.innerHTML = '<i class="bi bi-hand-thumbs-up-fill p-2"></i>' + like;
    } else {
        btnIcon.innerHTML = '<i class="bi bi-hand-thumbs-up p-2"></i>' + like;
    }
}