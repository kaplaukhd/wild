window.onload = () => {
    fillProductTable()
}
const baseUrl = 'http://194.58.120.217:7054/v1/api/products';

$(document).ready(() => {
    document.getElementById("searchInput").addEventListener(
        "input", findProduct
    )
})

function fillProductTable() {
    const allUsersTableBody = document.getElementById('allOrderTableBody')

    // $('#allProductTable').empty()
    fetch(baseUrl, {
        Content: "application/json",
        method: "GET"
    })
        .then(response => response.json())
        .then(data => {
            let columnContent = ''
            let isActive;
            data.forEach(element => {
                if (element.status === 'ACTIVE') {
                    isActive = 'В наличии';
                } else {
                    isActive = 'Нет в наличии';
                }
                let link = 'https://www.wildberries.ru/catalog/' + element.nmId + '/detail.aspx'
                columnContent += `<tr>
                    <td>${element.name}</td>
                    <td>${element.brand}</td>
                    <td>${element.price}</td>
                    <td>${element.salePrice}</td>
                    <td>${isActive}</td>
                    <td>
                      <a href="${link}" class="btn btn-primary">Перейти</a>
                     </td>
                </tr>
                `
            })
            allUsersTableBody.innerHTML = columnContent;
        })
}

function findProduct() {
    const allUsersTableBody = document.getElementById('allOrderTableBody')
    const rows = allUsersTableBody.getElementsByTagName("tr");
    const searchInput = document.getElementById("searchInput").value;

    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName("td");
        for (let j = 0; j < cells.length; j++) {
            if (cells[j].innerHTML.toLowerCase().indexOf(searchInput.toLowerCase()) > -1) {
                rows[i].style.display = "";
                break;
            } else {
                rows[i].style.display = "none";
            }
        }
    }
}