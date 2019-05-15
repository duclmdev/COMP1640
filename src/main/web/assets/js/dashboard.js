$(() => {
    $.ajax({
        url: "/dashboard",
        success: data => {
            console.log(data);
            const {name, submitted, chosen, first_deadline, second_deadline} = data[0];
            $("#submitted").html(submitted);
            $("#chosen").html(chosen);
            if (first_deadline) $("#first_deadline").html(first_deadline);
            if (second_deadline) $("#second_deadline").html(second_deadline);
            $(".py-name").html(name);
        }
    })
});