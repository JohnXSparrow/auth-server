function getUrlUsername() {
    // Simple function that relies on the exact url syntax
    var url=window.location.href;
    if(url.match('\\?username=')) {
        return url.split('=')[1];
    }
    return
}

function getModel() {
    return {
       currentPassword: $('#currentPassword').val(),
       newPassword: $('#newPassword').val(),
       newPasswordConf: $('#newPasswordConf').val(),
   };
}

function validateData() {
        var model = getModel();

        if (((model.newPassword || model.newPasswordConf) && (model.newPassword !== model.newPasswordConf))
            || model.currentPassword === model.newPassword
            || !model.currentPassword || !model.newPassword || !model.newPasswordConf) {
            $('#btnSubmit').prop('disabled', true);
        } else {
            $('#btnSubmit').prop('disabled', false);
        }

        if((model.newPassword && model.newPasswordConf) && (model.newPassword !== model.newPasswordConf)) {
            $('#pwNotMatch').prop('hidden', false);
        } else {
            $('#pwNotMatch').prop('hidden', true);
        }

        if (model.currentPassword && model.newPassword && (model.currentPassword === model.newPassword)) {
            $('#pwNotChange').prop('hidden', false);
        } else {
            $('#pwNotChange').prop('hidden', true);
        }


}

//function updatePassword() {
//    $.ajax({
//        type: 'POST',
//        url: '/auth/api/user/updatepassword',
////        dataType: 'json',
//        contentType: "application/json",
////        traditional: true,
//        data: JSON.stringify(getModel()),
//        success: function(data) {
//            console.log('success', data);
//            window.alert('Senha atualizados!');
//            window.location.replace('/auth/login')
//        },
//        error: function(data) {
//            console.log('error', data);
//            window.alert('Erro atualizando senha!');
//        }
//    });
//}

$(document).ready(function() {
    $('#username').val(getUrlUsername());
    $('.formInput').keyup(validateData)
    validateData()
});