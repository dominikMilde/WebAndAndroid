

class InsertionSort {
    constructor(size) {
        this.size = size;
        this.array = this.generateRandomBigArray(size);
    } 
    generateRandomBigArray(size){
        let array = [];
        while(size > 0){
            array.push(Math.random()*1000);
            size --;
        }
        return array;
    }
    get Size() { return this.size;}
    set Size(newSizeValue) {
        if(newSizeValue > this.size){
            throw new RangeError("New value cannot be larger than the current size!");
        }
        else
            this.size = size;
    }
    sort(){
        var i, j;
        var temp;
        for (i = 1; i<this.array.length; i++) {
            temp = this.array[i];
            for (j = i; j >= 1 && this.array[j - 1] > temp; j--)
            this.array[j] = this.array[j - 1];
            this.array[j] = temp;
        }
        console.log("Finished");
        return this.array;
    }   
}

try{
    console.log("Hello");
    let oneSort = new InsertionSort(100);
    oneSort.sort();
    console.log(typeof InsertionSort)
    oneSort.Size = 101;
}catch(err){
    if(err instanceof RangeError)
        console.log(err.name + ": " + err.message)
    else
        console("A sorting error has occured!");
}
