/*
let persons = ["Mark", "John", "Joe"];
persons[2] = "Susan";
console.log(persons);
persons[1] = function() { console.log("Hello from an array!") };
persons[1]();
for (let person of persons) {
  console.log(person); 
}
*/

let personMap = new Map([
  [1, "Mark"],
  [2, "John"],
  [3, "Joe"]
]);
console.log(personMap);
personMap.set(4, "Mary");
personMap.delete(2);
console.log(personMap);

for (let personKey of personMap.keys()) {
  console.log(personKey); 
}
for (let personValue of personMap.values()) {
  console.log(personValue); 
}
