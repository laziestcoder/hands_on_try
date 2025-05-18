$("#upload .submit-button").on("click", function () {
    var uploadUrl = getBaseUrl() + this.getAttribute("data-id");
    var fileInput = document.getElementById('file-input');

    if (fileInput.files.length === 0) {
        alert("Please select a file to upload.");
        return;
    }

    var formData = new FormData();

    formData.append('file', fileInput.files[0]);

    fetch(uploadUrl, {
        method: 'POST',
        body: formData,
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            return null;
        })
        .then(data => {
            fileInput.value = '';
            if (data != null) {
                errorMessageHide();
                alert('File uploaded successfully.');
            } else {
                errorMessage('Error uploading file!');
            }
        })
        .catch(error => {
            alert('File upload failed.');
            console.error('Error uploading file:', error);
        });
})