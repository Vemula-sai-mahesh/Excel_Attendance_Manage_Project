document.getElementById('uploadForm').addEventListener('submit', async function (event) {
    event.preventDefault();
    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];
    if (!file) {
        document.getElementById('error').textContent = 'Please select a file.';
        return;
    }

    const formData = new FormData();
    formData.append('ExcelFile', file);

    try {
        const response = await fetch('http://localhost:8080/api/excelData', {
            method: 'POST',
            body: formData,
            credentials: 'include', // Ensure cookies are sent if needed
        });

        if (response.ok) {
            const result = await response.json();
            document.getElementById('response').textContent = 'File uploaded successfully!';
            document.getElementById('error').textContent = '';
            displayData(result);
        } else {
            const errorText = await response.text();
            document.getElementById('response').textContent = '';
            document.getElementById('error').textContent = `File upload failed. Error: ${response.status} - ${errorText}`;
        }
    } catch (error) {
        document.getElementById('response').textContent = '';
        document.getElementById('error').textContent = `An error occurred: ${error.message}`;
    }
});

function displayData(data) {
    const dataForm = document.getElementById('dataForm');
    dataForm.innerHTML = ''; // Clear previous data if any

    data.sheetDTOList.forEach(sheet => {
        const sheetTitle = document.createElement('h3');
        sheetTitle.textContent = `Sheet Name: ${sheet.sheetName}`;
        dataForm.appendChild(sheetTitle);

        const colList = document.createElement('ul');
        sheet.sheetColumnDTOList.forEach(col => {
            const colItem = document.createElement('li');
            colItem.textContent = col.sheetColName;
            colList.appendChild(colItem);
        });

        dataForm.appendChild(colList);
    });
}
