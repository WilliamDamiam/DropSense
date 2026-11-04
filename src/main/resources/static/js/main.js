/* DropSense • Giao diện dùng chung
 * Chỉ xử lý tương tác giao diện. Không tính toán nguy cơ, không thay thế Java Backend.
 */
(function () {
  'use strict';

  var isLocalPreview = window.location.protocol === 'file:';

  function notify(message) {
    var old = document.querySelector('.toast-message');
    if (old) old.remove();
    var toast = document.createElement('div');
    toast.className = 'toast-message';
    toast.setAttribute('role', 'status');
    toast.textContent = message;
    document.body.appendChild(toast);
    window.setTimeout(function () { toast.remove(); }, 4300);
  }

  // Tìm kiếm gửi GET /students?keyword=... về StudentController.
  // Không chặn submit; Backend sẽ truy vấn MySQL theo họ tên.

  // Xác nhận xóa trước khi gửi POST /students/delete/{id}.
  document.querySelectorAll('[data-confirm-delete]').forEach(function (form) {
    form.addEventListener('submit', function (event) {
      var button = form.querySelector('[data-student-name]');
      var name = button ? button.getAttribute('data-student-name') : 'sinh viên này';
      if (!window.confirm('Bạn có chắc chắn muốn xóa ' + name + '?\nThao tác này không thể hoàn tác.')) {
        event.preventDefault();
        return;
      }
      if (isLocalPreview) {
        event.preventDefault();
        notify('Bản xem thử: chưa gửi yêu cầu xóa đến Backend.');
      }
    });
  });

  function validationText(field) {
    var value = field.value.trim();
    var labelNode = document.querySelector('label[for="' + field.id + '"]');
    var label = labelNode ? labelNode.textContent.replace('*', '').trim() : 'Trường này';
    if (field.validity.badInput) return 'Vui lòng nhập một số hợp lệ.';
    if (field.required && !value) return 'Vui lòng nhập/chọn ' + label.toLowerCase() + '.';
    if (!value || field.readOnly) return '';
    if (field.type === 'number') {
      var number = Number(value);
      if (!Number.isFinite(number)) return 'Vui lòng nhập một số hợp lệ.';
      var min = field.min !== '' ? Number(field.min) : -Infinity;
      var max = field.max !== '' ? Number(field.max) : Infinity;
      if (number < min || number > max) {
        if (Number.isFinite(max)) return label + ' phải nằm trong khoảng ' + min + ' đến ' + max + '.';
        return label + ' phải lớn hơn hoặc bằng ' + min + '.';
      }
      if (field.step === '1' && !Number.isInteger(number)) return label + ' phải là số nguyên.';
      if (!field.validity.valid) return 'Giá trị không đúng bước nhập cho phép.';
    }
    if (!field.checkValidity()) return 'Giá trị của ' + label.toLowerCase() + ' không hợp lệ.';
    return '';
  }

  document.querySelectorAll('[data-validate-form]').forEach(function (form) {
    var fields = Array.from(form.querySelectorAll('input, select'));
    function validate(field) {
      var message = validationText(field);
      var error = form.querySelector('[data-error-for="' + field.id + '"]');
      if (error) error.textContent = message;
      field.classList.toggle('is-invalid', Boolean(message));
      field.setAttribute('aria-invalid', message ? 'true' : 'false');
      return !message;
    }
    fields.forEach(function (field) {
      field.addEventListener('input', function () { if (field.classList.contains('is-invalid')) validate(field); });
      field.addEventListener('change', function () { validate(field); });
    });
    form.addEventListener('submit', function (event) {
      var firstInvalid = null;
      fields.forEach(function (field) { if (!validate(field) && !firstInvalid) firstInvalid = field; });
      if (firstInvalid) {
        event.preventDefault();
        firstInvalid.focus();
        notify('Vui lòng kiểm tra các trường được đánh dấu trước khi tiếp tục.');
        return;
      }
      if (form.hasAttribute('data-pending-submit')) {
        event.preventDefault();
        notify('Chức năng đánh giá đang chờ Backend Risk; chưa có kết quả thật.');
        return;
      }
      if (isLocalPreview) {
        event.preventDefault();
        notify('Bản xem thử: form hợp lệ về giao diện nhưng chưa được gửi đến Spring Boot.');
      }
    });
  });
})();
