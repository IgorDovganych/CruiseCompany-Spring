function getCruiseFinalPrice() {
    var baseCruisePrice, excursionsPrice,finalPrice,priceMultiplier ;
    baseCruisePrice = parseInt(document.getElementsByClassName("margin-left")[0].getAttribute("value"));

    priceMultiplier=1;
    for (var i=0; i<document.getElementsByName("selectedTicket").length; i++){
        if(document.getElementsByName("selectedTicket")[i].checked){
            priceMultiplier=parseFloat(document.getElementsByName("selectedTicket")[i].parentElement.parentElement.getAttribute("value"));
        }
    }

    excursionsPrice = 0;
    for (var i=0; i<document.getElementsByName("excursion").length; i++){
        if(document.getElementsByName("excursion")[i].checked){
            excursionsPrice+=parseInt(document.getElementsByName("excursion")[i].parentElement.parentElement.getAttribute("value"));
         }
    }
    finalPrice = baseCruisePrice*priceMultiplier+excursionsPrice;
    document.getElementById("finalPrice").textContent = ("Final price = " + finalPrice +".0 $");
}

function sendRequest(path, method='post') {

    const form = document.createElement('form');
    form.method = method;
    form.action = path;

    var ticketParams = new Object();
    ticketParams.cruiseId = document.getElementById("cruiseId").getAttribute("value");
    console.log("cruise id = " + ticketParams.cruiseId);
    ticketParams.ticketId = "";
    //Getting selected ticket id
    for (var i=0; i<document.getElementsByName("selectedTicket").length; i++){
        if(document.getElementsByName("selectedTicket")[i].checked){
            ticketParams.ticketId=document.getElementsByName("selectedTicket")[i].getAttribute("value");
        }
    }
    console.log("Selected ticket id = " + ticketParams.ticketId)

    ticketParams.excursionIds = "";
    //getting selected excurion ids
    var numberOfExcursions = document.getElementsByName("excursion").length;

    for (var i=0; i<numberOfExcursions; i++){
        if(document.getElementsByName("excursion")[i].checked){
            ticketParams.excursionIds+=document.getElementsByName("excursion")[i].getAttribute("value")+",";
        }
    }
    if(ticketParams.excursionIds!=""){
        ticketParams.excursionIds = ticketParams.excursionIds.slice(0, -1);
    }
    console.log("Selected excursion ids : "+ticketParams.excursionIds);


    for (const key in ticketParams) {
        console.log("key - " + key);
        if (ticketParams.hasOwnProperty(key)) {
            const hiddenField = document.createElement('input');
            hiddenField.type = 'hidden';
            hiddenField.name = key;
            hiddenField.value = ticketParams[key];

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}