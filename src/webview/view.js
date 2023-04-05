/**
 * Representation of HTML-view and some validation fnctions for inpts.
 */
class View{
    constructor(){
        this.master1 = "";
        this.master2 = "";
        this.ressource = "";
        this.password = "";
        this.valid = false;
        this.errorMessage = "";

        console.log("View");
    }

    readInputs(){
        this.master1 = document.getElementById("master1").value;
        this.master2 = document.getElementById("master2").value;
        this.ressource = document.getElementById("ressource").value;
        this.password = "";
        this.valid = false;
        this.errorMessage = "";
        this.setPasswordField("");
    }

    validateInput(){
        this.valid = false;
        if (this.master1 == ""){
            this.errorMessage = "Missing master password";
        }
        else{
            if (this.ressource == ""){
                this.errorMessage = "Missing ressource";
            }
            else{
                if (this.master2 !="" && this.master1 != this.master2){
                    this.errorMessage = "Master passwords don't match";
                }
                else{
                    this.valid = true;
                }
            }
        }
        
        return this.valid;    
    }

    setPasswordField(text){
        document.getElementById("password").value = text;
    }

    getMaster(){
        // TODO: Validation?
        return this.master1;
    }

    getRessource(){
        // TODO: Validation?
        return this.ressource;
    }

    getErrorMessage(){
        return this.errorMessage;
    }
}