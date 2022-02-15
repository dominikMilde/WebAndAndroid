function increaseNumberOf(name){
    if(localStorage.getItem(name) === null){
        localStorage.setItem(name, 1);
    }
    else{
        let num = parseInt(localStorage.getItem(name)) + 1;
        localStorage.setItem(name, num);
    }
};

