function addPortToRoute(id) {
    portName = document.getElementsByTagName("tr")[id].children[1].textContent;
    console.log(id, portName);

    var tableWithRoute = document.getElementById("route");


    var element = document.createElement("tr");
    var pointId = document.getElementById("route").children.length +1;
    element.innerHTML = "<td>"+pointId+"</td><td hidden>" +id + "</td><td>"+ portName + "</td><td><button onclick='removePortFromRoute(" + pointId + ")'>-</button></td>";

    tableWithRoute.appendChild(element);


}

function removePortFromRoute(pointId) {
    tableWithRoute = document.getElementById("route");
    for (var i = 0; i <tableWithRoute.children.length;i++){
        tr = tableWithRoute.children[i];
        if(tr.children[0].textContent==pointId){
            tr.remove();
        }
    }
}

function sendRequest(path, method='post') {
    const form = document.createElement('form');
    form.method = method;
    form.action = path;

    var cruiseDetails = new Object();
    var shipElement = document.getElementById("ships");
    cruiseDetails.shipId = shipElement.options[shipElement.selectedIndex].value;
    console.log("ship id = " + cruiseDetails.shipId);

    cruiseDetails.portIds = "";
    var numberOfPorts = document.getElementById("route").children.length;
    console.log("numberOfPorts = " + numberOfPorts);
    for (var i=0;i<numberOfPorts;i++){
        if(i!=0){
            cruiseDetails.portIds +=",";
        }
        cruiseDetails.portIds += document.getElementById("route").children[i].children[1].textContent;

    }
    console.log(cruiseDetails.portIds);
    cruiseDetails.startDate = document.getElementById("start").value;
    cruiseDetails.endDate = document.getElementById("end").value;
    cruiseDetails.basePrice = document.getElementById("basePrice").value;
    console.log("start = " + cruiseDetails.startDate);
    console.log("end = " + cruiseDetails.endDate);
    console.log("base price = " + cruiseDetails.basePrice );

    for (const key in cruiseDetails) {
        console.log("key - " + key);
        if (cruiseDetails.hasOwnProperty(key)) {
            const hiddenField = document.createElement('input');
            hiddenField.type = 'hidden';
            hiddenField.name = key;
            hiddenField.value = cruiseDetails[key];

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();

}