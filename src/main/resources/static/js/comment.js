
/**
 * 改变点赞状态
 * @param e
 */
function changeLikeCommentState(e) {
    var commentId = e.getAttribute("data-id");
    if (e.classList.contains("active")) {
        sendLikeState(0, commentId, 2);
        e.classList.remove("active");
    } else {
        sendLikeState(1, commentId, 2);
        e.classList.add("active");
    }
}
function sendLikeState(status, targetId, targetType) {
    $.ajax({
        type: "POST",
        url: "/favour",
        contentType: "application/json",
        data: JSON.stringify({
            "status": status,
            "targetId": targetId,
            "targetType": targetType
        }),
        success: function (response) {
            if (response.code != 200) {
                alert(response.message)
            }
        },
        dataType: "json"
    })
}
/**
 * 提交回复
 */
function postComment() {
    var questionId = $("#questionId").val();
    var content = $("#comment").val();
    postCommentToTarget(questionId, 1, content);
}
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    postCommentToTarget(commentId, 2, content);
}
/**
 * 发送请求获取目标评论记录
 * @param targetId
 * @param type
 * @param content
 */
function postCommentToTarget(targetId, type, content) {
    if (!content) {
        alert("想回复可不能啥话都不说吖(⊙o⊙)~~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    if (confirm(response.message)) {
                        window.open("https://github.com/login/oauth/authorize?client_id=6b94612764d44e4ce138&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}
/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");

    // 设置二级评论折叠状态
    $("#comment-" + id).collapse('toggle');
    if (e.classList.contains("active")) {
        e.classList.remove("active");
    } else {
        e.classList.add("active");
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length == 1) {

            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data, function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<a/>", {
                        "href": "#"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded img-size-head",
                        "src": comment.user.avatarUrl
                    })));

                    var mediabodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<div/>", {
                        "class": "media-heading"
                    }).append($("<a/>", {
                        "href": "#"
                    }).append($("<span/>", {
                        "text": comment.user.name
                    }))).append($("<span/>", {
                        "text": " | " + dayjs(comment.gmtCreate).format("YYYY-MM-DD")
                    })).append($("<a/>",{
                        "href": "#"
                    }).append(
                        $("<span/>", {
                        "class": "comment-float",
                        "text": "回复",
                        "data-id": comment.id
                    })))).append($("<div/>", {
                        "text": comment.content
                    }));

                    var mediaElement = $("<div>", {
                      "class": "media"
                    }).append(mediaLeftElement)
                        .append(mediabodyElement)
                        .append($("<hr/>", {
                            "class": "comment-second-menu"
                        }));

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });

            });
        }
        console.info(id);
    }
}
/**
 * 取消回复二级评论
 * @param e
 */
function collapseSecondComments(e) {
    $("#comment-" + e.getAttribute("data-id")).collapse("hide");
}