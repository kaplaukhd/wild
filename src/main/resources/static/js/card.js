window.onload = () => {
    fillProductTable()
}
const baseUrl = 'http://194.58.120.217:7054/v1/api/products';


$(document).ready(() => {
    document.getElementById("searchInput").addEventListener("input", findProduct)
})

function fillProductTable() {
    const allUsersTableBody = document.getElementById("cards");

    fetch(baseUrl, {
        headers: {
            "Content-Type": "application/json",
        },
        method: "GET",
    })
        .then((response) => response.json())
        .then((data) => {
            let columnContent = "";

            data.forEach((element) => {
                let cardDiv = document.createElement("div"); // создаем новый элемент cardDiv на каждой итерации цикла
                cardDiv.classList.add("card");

                let link =
                    "https://www.wildberries.ru/catalog/" +
                    element.nmId +
                    "/detail.aspx";
                columnContent += `
          <h3 style="text-align: center">${element.name}</h3>
          <p>${element.brand}</p>
           <div class="col-9">
            <p style="text-decoration: line-through;">${element.price}</p>
               <h3>${element.salePrice}</h3>
               </div>
          <p>${element.color}</p>
          <form action="${link}" target="_blank">
            <button class="button button4">Перейти</button>
          </form>
      `;
                cardDiv.innerHTML = columnContent;
                allUsersTableBody.appendChild(cardDiv); // добавляем текущий элемент cardDiv в allUsersTableBody на каждой итерации цикла
                columnContent = ""; // сбрасываем значение columnContent для следующей итерации цикла
            });
        })
        .catch((error) => console.error(error));
}


function findProduct() {
    const allCards = document.querySelectorAll('.card');
    const searchInput = document.getElementById('searchInput').value.toLowerCase();

    allCards.forEach(card => {
        if (card.innerText.toLowerCase().indexOf(searchInput) > -1) {
            card.style.display = '';
        } else {
            card.style.display = 'none';
        }
    });
}
