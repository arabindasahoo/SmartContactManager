const toggleSidebar = () => {

if($('.sidebar').is(":visible")){
//true

$(".sidebar").css("display","none");
$(".content").css("margin-left","2%");

}   
else
{

    //false
    $(".sidebar").css("display","block");
    $(".content").css("margin-left","20%");

}    
}


function deleteContact(cid)
{
	  swal({
  	  title: "Are you sure?",
  	  text: "You want to delete?",
  	  icon: "warning",
  	  buttons: true,
  	  dangerMode: true,
  	})
  	.then((willDelete) => {
  	  if (willDelete) {
  	    
  		  window.location="/user/delete/"+cid;
  		  
  	  } else {
  	    swal("Contact is safe");
  	  }
  	});
}


//Java Script for searching contacts by name

const search = () => {

let query = $("#search-input").val();

if(query == "")
{
	console.log(query);
$(".search-result").hide();
}else{

	let url = `http://localhost:3333/search/${query}`;

	fetch(url)
	.then((response) => {

		return response.json();
	})
	.then((data) =>{

		let text=`<div class="list-group">`;

			data.forEach((contact) =>{
				text += `<a href='/user/contact/${contact.cId}' class='list-group-item list-group-action'>${contact.name}</a>`

			})

		 text +=`</div>`;


		 $(".search-result").html(text);
		 $(".search-result").show();
	})

	
}

}