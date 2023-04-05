/**
 * Binding model-view-controller in connection with HTML-representation
 */

// Model
const h = new MarkovHumanizer(mdmapping);
const sg = new seedGenerator();
const gp = new PasswordGenerator(sg, h);

// View
const v = new View();

// Controller
/**
 * Generate password as-is, all lower case.
 */
function generateLower(){
    v.readInputs();
    var valid = v.validateInput();
    if (valid){
        let pass = gp.generatePassword(v.getMaster(), v.getRessource());
        v.setPasswordField(pass);
    }
    else{
        v.setPasswordField(v.getErrorMessage());
    }
}

/**
 * Generate password with first letter uppercase.
 */
function generateUpper(){    
    v.readInputs();
    var valid = v.validateInput();
    if (valid){
        let pass = gp.generatePassword(v.getMaster(), v.getRessource());
        // set upper case for first letter
        let upass = pass.charAt(0).toUpperCase() + pass.slice(1);
        v.setPasswordField(upass);
    }
    else{
        v.setPasswordField(v.getErrorMessage());
    }
}