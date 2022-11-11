window.onload = function () {
    let postIdDiv = document.getElementById('postIdDiv');
    let btnAddComm = document.getElementById('btnAddComm');

    if(postIdDiv !== null) {
        btnAddComm.addEventListener('click', function () {
            let comment = document.getElementById('comment').value;
            let postId = postIdDiv.innerText;
            if (comment !== "") {
                return fetch('http://localhost:8888/comment?postIndex=' + postId + '&comment=' + comment)
                    .then((response) => {
                        return response.json();
                    }).then((comment) => {
                        addCommentCard(comment);
                    })
            } else {
                showPopup("Your comment can not be empty", "Error", false)
            }

        }, false);
    }
}


function addCommentCard(comment) {
    document.getElementById('comment').value = "";
    let commDivClone = document.getElementById('commentDivInvisible').cloneNode(true);
    commDivClone.style.display = "block";
    let childDiv = commDivClone.children[0];

    childDiv.children[0].children[0].children[0].children[0].children[0].innerText = comment.authorName;
    childDiv.children[0].children[0].children[0].children[0].children[1].innerText = comment.created_at;
    childDiv.children[0].children[0].children[0].children[1].innerText = comment.text;

    document.getElementById('commentDiv').prepend(childDiv);

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