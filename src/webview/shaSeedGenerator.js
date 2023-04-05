/**
 * Class for handling cryptographic operations. Calculates pseudo-random
 * entropy from provided iputs.
 */

// hash description
const hashDesc = "SHA-512";
const hashLength = 64;
const entropyByteLength = 5;
const encoding = "UTF8"

//
class seedGenerator{
    constructor(){
        return;
    }

    /**
     * Calculate SHA-512 hash from concatenation of master password and ressource
     * @param {String} master Master password
     * @param {String} ressource Ressource name
     * @returns Byte array with hash
     */
    produceHMAC(master, ressource){
        const shaObj = new jsSHA(hashDesc, "TEXT", { encoding: encoding });
        shaObj.update(master+ressource);

        return shaObj.getHash("UINT8ARRAY");
    }

    /**
     * Calculate pseudo-random entropy by folding and XOR of hash
     * @param {String} master Master password
     * @param {String} ressource Ressource name
     * @returns Byte array with entropy
     */
    generateSeed(master, ressource){

        var digest = this.produceHMAC(master, ressource);

        // fold hash-value to target entropy
        let numFoldings = Math.trunc(hashLength / entropyByteLength);
        var entropy = new Uint8Array(entropyByteLength);
        
        for (var i = 0; i < numFoldings; i++) {
            let mask = new Uint8Array(digest.slice(entropyByteLength * i,
                entropyByteLength * (i + 1)));
            //XOR(entropy, mask);
            let t = entropy.map((x,i)=>x^mask[i]);
            entropy = t;    
        }

        return entropy;
    }
}