let destinationsAll = [];
const searchBtn = document.getElementById("SearchBtn");
function getDestinations() {
    fetch('api/destinations')
        .then(res => res.json())
        .then((destinations) => {
            destinations.forEach(destination => {
                destinationsAll.push({
                        id: destination.id,
                        airportCode: destination.airportCode,
                        airportName: destination.airportName,
                        cityName: destination.cityName,
                        timezone: destination.timezone,
                        countryName: destination.countryName
                    }
                )

                let optionFrom = document.createElement('option'); //создаем и добавляем в город в "Откуда"
                optionFrom.text = destination.cityName;
                optionFrom.index = destination.id;
                document.querySelector('#destinationFrom').add(optionFrom);

                let option = document.createElement('option'); //создаем и добавляем в город в "Куда"
                option.text = destination.cityName;
                option.index = destination.id;
                document.querySelector('#destinationTo').add(option);
            })
        });
}

async function createSearch() {
    let destinationFrom = destinationsAll[document.getElementById('destinationFrom').selectedIndex]; //забираем выбранный город
    let destinationTo = destinationsAll[document.getElementById('destinationTo').selectedIndex];
    console.log(destinationFrom);
    console.log(destinationTo);
    fetch('api/search', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            from: destinationFrom,
            to: destinationTo,
            departureDate: document.getElementById('departureDate').value,
            returnDate: document.getElementById('returnDate').value,
            numberOfPassengers: document.getElementById('numberOfPassengers').value,
        })
    }).then((response) => {
        if (!response.ok) {
            alert("Ошибка HTTP: " + response.status);
            throw new Error("Network response was not ok");
        } else {
            console.log("ok")
            return response.json();
        }
    })
        .then(searchResult => {
            location.replace('/searchResult?id=' + searchResult.id); //редирект
        })
}

searchBtn.addEventListener('click', ()=> {
    createSearch();
})


getDestinations();
