
class PasswordGenerator {
    constructor(sg, h) {
        this.seedGenerator = sg;
        this.hmanizer = h;
    }

    generatePassword(master, ressource){
        let seed = sg.generateSeed(master, ressource);
        let pass = h.humanize(seed);

        return pass;
    }
}