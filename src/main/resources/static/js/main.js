
document.addEventListener("DOMContentLoaded", function()
{
    // 1. Xác nhận trước khi xóa dữ liệu
    var deleteForms = document.querySelectorAll("form[data-confirm]");

    for (var i = 0; i < deleteForms.length; i++)
    {
        deleteForms[i].addEventListener("submit", function(event)
        {
            var message = this.getAttribute("data-confirm");

            if (!confirm(message))
            {
                event.preventDefault();
            }
        });
    }

    // 2. Tự động ẩn thông báo thành công sau 5 giây
    var successAlerts = document.querySelectorAll(".alert-success[data-auto-hide]");

    for (var i = 0; i < successAlerts.length; i++)
    {
        var alert = successAlerts[i];

        setTimeout(function(element)
        {
            element.classList.add("d-none");
        }, 5000, alert);
    }
});
