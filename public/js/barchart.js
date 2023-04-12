
// <block:setup:1>

fetch("/traffic.json").then(result => {
  return result.json()
}).then(json => {
  console.log(json)
  const values = Object.values(json);
  const rooms = ["in2", "Old Cafe", "IRC", "TV Pit", "SSA", "Loft", "Stunion"];
  const data = {
    labels: rooms,
    datasets: [{
      label: 'Connections in an Area',
      data: values,
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(255, 159, 64, 0.2)',
        'rgba(255, 205, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(153, 102, 255, 0.2)',
        'rgba(201, 203, 207, 0.2)'
      ],
      borderColor: [
        'rgb(255, 99, 132)',
        'rgb(255, 159, 64)',
        'rgb(255, 205, 86)',
        'rgb(75, 192, 192)',
        'rgb(54, 162, 235)',
        'rgb(153, 102, 255)',
        'rgb(201, 203, 207)'
      ],
      borderWidth: 1
    }]
  };
  // </block:setup>

  // <block:config:0>
  const config = {
    type: 'bar',
    data: data,
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    },
  };
  // </block:config>

  const ctx = document.getElementById('Traffic');
  new Chart(ctx, config);
})