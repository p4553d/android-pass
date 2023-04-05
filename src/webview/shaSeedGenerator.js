// hash description
const hashDesc = "SHA-512";
const hashLength = 64;
const entropyByteLength = 5;
const encoding = "UTF8"

function produceHMAC(master, ressource){
    const shaObj = new jsSHA(hashDesc, "TEXT", { encoding: encoding });
    shaObj.update(master+ressource);

    return shaObj.getHash("UINT8ARRAY");
}

function generateSeed(digest){

    // fold hash-value to target entropy
	numFoldings = Math.trunc(hashLength / entropyByteLength);
	entropy = new Uint8Array(entropyByteLength);
    
    for (var i = 0; i < numFoldings; i++) {
	    let mask = new Uint8Array(digest.slice(entropyByteLength * i,
		    entropyByteLength * (i + 1)));
	    //XOR(entropy, mask);
        let t = entropy.map((x,i)=>x^mask[i]);
        entropy = t;    
	}

    return entropy;
}