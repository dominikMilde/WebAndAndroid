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
            console.log("Error! This class does not " +
                "allow increasing size without enlarging the array");
        }
        else
            this.size = size;
    }
    sort(){
        for (let i = 1; i < this.array.length; i++) {
            let temp = this.array[i];
            let j = i;
            for (; j >= 1 && this.array[j - 1] > temp; j--)
        	      this.array[j] = this.array[j - 1];
            this.array[j] = temp;
        }
        return this.array;
    }     
}

console.log("Hello before InsertionSort");
let oneSort = new InsertionSort(100);
oneSort.sort();
console.log(oneSort.array);

console.log(typeof InsertionSort)
oneSort.Size = 101;


