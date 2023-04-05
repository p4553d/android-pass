/**
 * Any actual unit test framework for javascript is basicaly unusable complexity stack
 * beyond any reason and maintanability.
 * Here we go an do it like in old days.
 */

const th = new MarkovHumanizer(mdmapping);
const tsg = new seedGenerator();
const tgp = new PasswordGenerator(tsg, th);

function testSeedGeneratorHMAC() {
    let m="test";
    let r="t";
    let res = tsg.produceHMAC(m,r);
    let ores = new Uint8Array([
        63,35,180,246,170,21,117,150,204,15,133,158,
        61,33,185,13,18,190,6,215,103,154,153,191,50,
        214,253,181,229,197,133,24,22,143,96,196,64,
        197,83,31,21,75,22,217,209,243,30,42,89,250,
        156,67,140,190,248,27,130,239,196,7,120,96,
        200,237
    ]);
    console.assert(JSON.stringify(res) == JSON.stringify(ores));
}

function testSeedGeneratorSeed() {
    let m="test";
    let r="t";
    let res = tsg.generateSeed(m,r);
    let ores = new Uint8Array([51,113,1,97,151]);

    console.assert(JSON.stringify(res) == JSON.stringify(ores));
}

function testMarkovHumanizerBigint() {
    let seed = new Uint8Array([51,113,1,97,151]);
    let res = bufToBn(seed);
    let ores = 220939248023n;

    console.assert(res == ores);
}

function testMarkovHumanizerHumanize() {
    let seed = new Uint8Array([50,114,100,98,251]);
    let res = th.humanize(seed);
    let ores = "ifelvellu-183";

    console.assert(res == ores);
}

function testPasswordGenerator1() {
    let m="test";
    let r="t";
    let res = tgp.generatePassword(m,r);
    let ores = "dwenbentecrea-218";

    console.assert(res == ores);
}
function testPasswordGenerator2() {
    let m="12345678901";
    let r="google.de";
    let res = tgp.generatePassword(m,r);
    let ores = "whoddength-aid-745";

    console.assert(res == ores);
}
function testPasswordGenerator3() {
    let m="abcdefghijk";
    let r="google.de";
    let res = tgp.generatePassword(m,r);
    let ores = "jalreans-th--266";

    console.assert(res == ores);
}

// Run
testSeedGeneratorHMAC();
testSeedGeneratorSeed();

testMarkovHumanizerBigint();
testMarkovHumanizerHumanize();

testPasswordGenerator1();
testPasswordGenerator2();
testPasswordGenerator3();