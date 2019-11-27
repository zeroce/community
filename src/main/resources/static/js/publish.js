/**
 * 标签提示
 */
function alternativeTagStatus() {
    if ($("#alternative-tags").css("display") == "none") {
        $("#alternative-tags").css("display") == "block";
        $("#alternative-tags").show();
    } else {
        $("#alternative-tags").css("display") == "none";
        $("#alternative-tags").hide();
    }
}
/**
 * 标签选择
 * @param e
 */
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previousTag = $("#tag").val();
    if (previousTag.indexOf(value) == -1) {
        if (previousTag) {
            $("#tag").val(previousTag + "," + value);
        } else {
            $("#tag").val(value);
        }
    }
}
/**
 * 创建新标签
 */
function createTagFormSubmit() {
    $.ajax({
        type: "POST",
        url: "/tag/create",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            tagName: $('#createTagName').val(),
            tagTypeName: $('#createTagType').val(),
            tagDescription: $('#createTagContent').text()
        }),
        success: function (response) {
            if (response.code == 200) {
                alert("创建成功！！");
            } else {
                alert(response.message);

            }
        }
    })
}

