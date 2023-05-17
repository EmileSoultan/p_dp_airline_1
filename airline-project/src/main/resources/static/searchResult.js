function createDepartRow(departFlight) { //функция для создания заполненной строки таблицы "Туда"
    console.log(departFlight);
    const tr = document.createElement("tr"); //создаем строку таблицы

    const departAirportCodeFromTd = document.createElement("td"); //создаем столбец
    departAirportCodeFromTd.textContent = departFlight.from.airportCode;
    tr.appendChild(departAirportCodeFromTd); //добавляем данные в столбец строки

    const departAirportNameFromTd = document.createElement("td");
    departAirportNameFromTd.textContent = departFlight.from.airportName;
    tr.appendChild(departAirportNameFromTd);

    const departDepartureDateTimeTd = document.createElement("td");
    departDepartureDateTimeTd.textContent = departFlight.departureDateTime;
    tr.appendChild(departDepartureDateTimeTd);

    const departAirportCodeToTd = document.createElement("td");
    departAirportCodeToTd.textContent = departFlight.to.airportCode;
    tr.appendChild(departAirportCodeToTd);

    const departAirportNameToTd = document.createElement("td");
    departAirportNameToTd.textContent = departFlight.to.airportName;
    tr.appendChild(departAirportNameToTd);

    const departArrivalDateTimeTd = document.createElement("td");
    departArrivalDateTimeTd.textContent = departFlight.arrivalDateTime;
    tr.appendChild(departArrivalDateTimeTd);

    return tr;
}

function createReturnRow(returnFlight) { //функция для создания заполненной строки таблицы "Обратно"
    console.log(returnFlight);
    const tr = document.createElement("tr");

    const returnAirportCodeFromTd = document.createElement("td");
    returnAirportCodeFromTd.textContent = returnFlight.from.airportCode;
    tr.appendChild(returnAirportCodeFromTd);

    const returnAirportNameFromTd = document.createElement("td");
    returnAirportNameFromTd.textContent = returnFlight.from.airportName;
    tr.appendChild(returnAirportNameFromTd);

    const returnDepartureDateTimeTd = document.createElement("td");
    returnDepartureDateTimeTd.textContent = returnFlight.departureDateTime;
    tr.appendChild(returnDepartureDateTimeTd);

    const returnAirportCodeToTd = document.createElement("td");
    returnAirportCodeToTd.textContent = returnFlight.to.airportCode;
    tr.appendChild(returnAirportCodeToTd);

    const returnAirportNameToTd = document.createElement("td");
    returnAirportNameToTd.textContent = returnFlight.to.airportName;
    tr.appendChild(returnAirportNameToTd);

    const returnArrivalDateTimeTd = document.createElement("td");
    returnArrivalDateTimeTd.textContent = returnFlight.arrivalDateTime;
    tr.appendChild(returnArrivalDateTimeTd);

    return tr;
}

function getSearchResult() {
    return fetch("http://localhost:8080/api/search/" + getIdFromURL(),{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
}

function getIdFromURL() {
    let paramsString = document.location.search; // получаем ?id=...
    let searchParams = new URLSearchParams(paramsString);
    return searchParams.get("id"); //получаем и возвращаем значение id
}

getSearchResult().then(searchResult => {
    console.log(searchResult);
    for (let i = 0; i < searchResult.departFlight.length; i++) {
        document.getElementById("DepartTable").appendChild(createDepartRow(searchResult.departFlight[i]));
    }
    for (let i = 0; i < searchResult.returnFlight.length; i++) {
        document.getElementById("ReturnTable").appendChild(createReturnRow(searchResult.returnFlight[i]));
    }
});
