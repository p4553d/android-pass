/**
 * 
 */
function getValidatedInputs(){
    master1 = document.getElementById("master1").value;
    master2 = document.getElementById("master2").value;
    ressource = document.getElementById("ressource").value;

    if (master1== "" || ressource == "" || (master2 !="" && master1 != master2)){
        r = null;
    }
    else {
        r = {"m1":master1, "m2":master2, "r":ressource};
    }
   
    return r;
}

function setPasswordField(text, errorstate = false){
    document.getElementById("password").value = text;
}

function generateLower(){
    input = getValidatedInputs();
    if (input == null){
        setPasswordField(" - invalid Input -", true);
        return;
    }
}

function generateUpper(){
    
}