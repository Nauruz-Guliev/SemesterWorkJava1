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
    let btnIcon = document.getElementById('likeIcon');
    if (like > parseInt(btnIcon.innerText)) {
        btnIcon.innerHTML = '<i class="bi bi-hand-thumbs-up-fill p-2"></i>' + like;
    } else {
        btnIcon.innerHTML = '<i class="bi bi-hand-thumbs-up p-2"></i>' + like;
    }
}


let checkCookie = function () {
    const currentCookie = document.cookie;
    let cookie = divideCookie(currentCookie);
    // если ключ куки содержит слово message, то показываю диалог
    if (cookie[0].includes("Message")) {
        showPopup(cookie[1].replaceAll("-", " "), cookie[0].replaceAll('Message', ' message'));
        // удаляю сообщение после того, как оно было показано
        document.cookie = cookie[0] + '=; Max-Age=0'
    }
};

function divideCookie(cookie) {
    return cookie.split('=');
}
// с интервалом проверяю есть ли cookie
window.setInterval(checkCookie, 100);


const showPopup = (message, title) => {
    let myModal = new bootstrap.Modal(document.getElementById("modal"), {});
    document.getElementById('message-body').innerText = message;
    document.getElementById('modal-title').innerHTML = title;
    myModal.show();
}


function addCommentCard(comment) {
    debugger;
    let clone = document.getElementById('commentDiv').children[0].cloneNode(true);

    clone.children[0].children[0].children[0].children[0].children[0].innerText = comment.authorName;
    clone.children[0].children[0].children[0].children[0].children[1].innerText = comment.created_at;
    clone.children[0].children[0].children[0].children[1].innerText = comment.text;

    document.getElementById('commentDiv').prepend(clone);

    /*
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

     */
}