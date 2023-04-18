fetch(`/history.json`).then(result => {
    return result.json()
  }).then(json => {
    console.log(json)
    const values = Object.values(json);
    const rooms = ["10 hours", "9 hours", "8 hours", "7 hours", "6 hours", "5 hours", "4 hours", "3 hours", "2 hours", "1 hour"];
    const data = {
      labels: rooms,
      datasets: [{
        label: 'Connections in an Area',
        data: values
      }]
    };
    // </block:setup>
  
    // <block:config:0>
    const config = {
      type: 'line',
      data: data,
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      },
    };
  
    const ctx = document.getElementById('history');
    new Chart(ctx, config);
  }).catch(error=> {})