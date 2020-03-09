function useGravatar() {
    $.ajax({
        method: 'POST',
        url: '/auth/api/user/avatar/usegravatar?current',
        success: function(data) {
            console.log('success', data);
            getData(function(currentUser) {
                var validateData = validateDataFn(currentUser);
                $('.formInput').keyup(validateData)
                validateData();
            });
        },
        error: function(data) {
            console.log('error', data);
            window.alert('Erro atualizando imagem!');
            getData(function(currentUser) {
                var validateData = validateDataFn(currentUser);
                $('.formInput').keyup(validateData)
                validateData();
            });
        }
    });
}



function getData (successFn) {
    $.ajax({
        method: 'GET',
        url: '/auth/api/user?current',
        dataType: 'json',
        traditional: true,
        success: function(user) {
            console.log('read success', user);

            $('#email').val(user.email);
            $('#name').val(user.name);
            $('#avatar').attr('data', user.avatarUrl);
//            if(user.avatarUrl.match('gravatar\\.com')) {
//                $('#avatarLabel').text('Imagem de perfil (usando Gravatar)');
//            } else {
//                $('#avatarLabel').text('Imagem de perfil (usando Gravatar)');
//            }

            $('#role')
                .find('option')
                .remove()
                .end();
            $('#role').append(
                new Option(" ", null, true, true)
            );

            if(user.company && user.company.roles) {
                user.company.roles.forEach(function (role) {
                    $('#role').append(
                        new Option(role.description, role.id, true, true)
                    );
                });
                if(user.companyRole) {
                    $('#role').val(user.companyRole.id);
                } else {
                    $('#role').val(null);
                }
            }

            if(user.company) $('#company').val(user.company.tradingName);

            if (user.hasGravatar) {
                if(user.avatarUrl.match('gravatar\\.com')) {
                    $('#gravatarOpt').hide();
                    $('#gravatarInUse').show();
                } else {
                    $('#gravatarOpt').show();
                    $('#gravatarInUse').hide();
                }
            } else {
                $('#gravatarOpt').hide();
                $('#gravatarInUse').hide();
            }

            $('#currentPassword').val(null);
            $('#newPassword').val(null);
            $('#newPasswordConfirm').val(null);



            if(successFn) successFn(user)
        },
        error: function(data) {
            console.log('error', data);
            window.alert('Erro buscando dados!');
        }
    });

}

function updateUser() {
    var role = $('#role').val();
    var updatedUser = {
      name: $('#name').val(),
      companyRole: role ? null : { id: role }
    };

    $.ajax({
        method: 'PUT',
        url: '/auth/api/user?current',
//        dataType: 'json',
        contentType: "application/json",
//        traditional: true,
        data: JSON.stringify(updatedUser),
        success: function(data) {
            console.log('success', data);
            window.alert('Dados atualizados!');
            getData(function(currentUser) {
                var validateData = validateDataFn(currentUser);
                $('.formInput').keyup(validateData)
                validateData();
            });
        },
        error: function(data) {
            console.log('error', data);
            window.alert('Erro atualizando dados!');
            getData(function(currentUser) {
                var validateData = validateDataFn(currentUser);
                $('.formInput').keyup(validateData)
                validateData();
            });
        }
    });
}

function validateDataFn(currentUser) {
    return function (event) {
        var model = {
            name: $('#name').val(),
            roleId: $('#role').val(),
        }

        console.log('validate', event, currentUser, model);

        if(((model.name && model.name !== currentUser.name)
           || (!currentUser.companyRole && model.roleId)
           || (currentUser.companyRole && !model.roleId)
           || (model.roleId && currentUser.companyRole && model.roleId !== currentUser.companyRole.id))) {
            $('#btnSubmit').prop('disabled', false);
        } else {
            $('#btnSubmit').prop('disabled', true);
        }

    }
}

function chooseFile() {
    var fi = $('#fileInput');
    fi.click();
}

function uploadFile(file) {
    if(file.size > 51200) {
        window.alert('Arquivo muito grande. Máx 50kb');
    }

    var formData = new FormData();
    formData.append('file', file)
    $.ajax({
        url: '/auth/api/user/avatar?current',
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        method: 'POST',
        success: function (a) {
            console.log('Upload OK: ', a);
            getData(function(currentUser) {
                var validateData = validateDataFn(currentUser);
                $('.formInput').keyup(validateData)
                validateData();
            });
        },
        error: function (a) {
            console.log('Upload ERR: ', a);
            getData(function(currentUser) {
                var validateData = validateDataFn(currentUser);
                $('.formInput').keyup(validateData)
                validateData();
            });
        }
    });
}

function submitPasswordChange(passwordChange) {
    $.ajax({
        method: 'POST',
        url: '/auth/api/user/updatepassword',
        cache: false,
        data: JSON.stringify(passwordChange),
        contentType: "application/json",
        success: function(data) {
            console.log('success', data);
            window.alert('Senha atualizada com sucesso');

            $('#passwordChangeNewConfirm').prop('disabled', false);
            $('#passwordChangeCurrent').prop('hidden', true);
            $('#passwordChangeNew').prop('hidden', true);
            $('#passwordChangeNewConfirm').prop('hidden', true);
            $('#passwordView').prop('hidden', false);
            $('.formInput').prop('disabled', false);

            getData(function(currentUser) {
                var validateData = validateDataFn(currentUser);

                $('.formInput').keyup(validateData)
                validateData();
            });
        },
        error: function(data) {
            console.log('error', data);
            window.alert('Erro atualizando senha!');

            $('#passwordChangeNewConfirm').prop('disabled', false);
            $('#passwordChangeCurrent').prop('hidden', true);
            $('#passwordChangeNew').prop('hidden', true);
            $('#passwordChangeNewConfirm').prop('hidden', true);
            $('#passwordView').prop('hidden', false);
            $('.formInput').prop('disabled', false);

            getData(function(currentUser) {
                var validateData = validateDataFn(currentUser);
                $('.formInput').keyup(validateData)
                validateData();
            });
        }
    });
}


function changePassword() {
    $('.formInput').prop('disabled', true);
    $('#btnSubmit').prop('disabled', true);
    $('#passwordView').prop('hidden', true);
    $('#passwordChangeCurrent').prop('hidden', false);
    $('#currentPassword').focus();

}

function passwordCurrentOK() {
    $('#passwordChangeCurrent').prop('hidden', true);
    $('#passwordChangeNew').prop('hidden', false);
    $('#newPassword').focus();
}

function passwordNewOK() {
    $('#passwordChangeNew').prop('hidden', true);
    $('#passwordChangeNewConfirm').prop('hidden', false);
    $('#newPasswordConfirm').focus();
}

function passwordNewConfirmOK() {

    $('#passwordChangeNewConfirm').prop('disabled', true);

    var passwordChange = {
        currentPassword: $('#currentPassword').val(),
        newPassword: $('#newPassword').val()
    };
    var passwordConfirm = $('#newPasswordConfirm').val()

    if(passwordConfirm === passwordChange.newPassword) {
        submitPasswordChange(passwordChange);
    } else {
        window.alert('Confirmação da senha não está igual!');

        $('#passwordChangeNewConfirm').prop('disabled', false);
        $('#passwordChangeCurrent').prop('hidden', true);
        $('#passwordChangeNew').prop('hidden', true);
        $('#passwordChangeNewConfirm').prop('hidden', true);
        $('#passwordView').prop('hidden', false);
        $('.formInput').prop('disabled', false);

        getData(function(currentUser) {
            var validateData = validateDataFn(currentUser);
            $('.formInput').keyup(validateData)
            validateData();
        });
    }



}

$(document).ready(function() {

    getData(function(currentUser) {
        $('.formInput').keyup(validateDataFn(currentUser))
        $('select.formInput').change(validateDataFn(currentUser))
    });

    $('#fileInput').change(function() {
        console.log('Upload: ', this.files[0]);
        uploadFile(this.files[0]);
    });
    $('#name').focus();


});
