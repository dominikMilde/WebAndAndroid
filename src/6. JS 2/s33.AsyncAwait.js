async function GetUniversity(country) {
    let universityResponse = await fetch('http://universities.hipolabs.com/search?country=' + country);
    let universityResponseJSON = await universityResponse.json(); 
    console.log(universityResponseJSON);
    //if(universityResponse != null  && universityResponse.length != 0){
    if(universityResponseJSON && universityResponseJSON.length){
        return universityResponseJSON[0].name;
    }
    return null;
}
let universityCroatia = GetUniversity("croatia").then(function(result){
    console.log(result);
});
