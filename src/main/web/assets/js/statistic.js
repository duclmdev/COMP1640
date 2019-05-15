$.ajax({
    url: '/statistic',
    data: {year: 2},
    success: data => {
        let dates = [];
        const raw = [];
        const faculties = [];
        let max = 0;
        data.forEach(datum => {
            if (!dates.includes(datum.submit_date)) {
                dates.push(datum.submit_date);
            }
            if (!faculties.includes(datum.faculty_id)) {
                raw[datum.faculty_id] = {length: 0};
                faculties.push(datum.faculty_id)
            }
            const f = raw[datum.faculty_id];
            f[datum.submit_date] = datum.count;
            if (max < datum.count) max = datum.count;
            f.length++;
        });

        const submissions = [];
        dates = dates.splice(-7, 7);
        dates.forEach(date => {
            faculties.forEach(f => {
                if (!submissions[f]) submissions[f] = [];
                submissions[f].push(raw[f][date] ? raw[f][date] : 0);
            })
        });
        console.log(submissions);
        const chartData = {
            labels: dates,
            series: submissions.filter(e => e !== null)
        };
        const chartOptions = {
            lineSmooth: false,
            axisY: {offset: 10},
            low: 0,
            high: max
        };
        Chartist.Line('#chart-submissions', chartData, chartOptions);

        const percents = countPercent(raw).filter(e => e !== null);
        const labels = percents.map(p => `${p}%`);
        Chartist.Pie('#chart-contribution', {labels: labels, series: percents});

    }
});

const countPercent = submissions => {
    const percents = [];
    const sum = submissions.reduce((a, b) => a + b.length, 0);
    for (let i = 0; i < submissions.length; i++) {
        const raw = submissions[i];
        if (!raw) continue;
        if (raw) {
            console.log(raw);
            percents[i] = Math.round(raw.length / sum * 100);
        }
    }
    return percents;
};
