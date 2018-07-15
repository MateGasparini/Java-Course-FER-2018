loadTags();

/**
 * Loads the tags using REST and appends them (as form input buttons) to the HTML.
 */
function loadTags() {
	$.ajax({
		url : "rest/tags",
		data : {
			dummy : Math.random()
		},
		dataType : "json",
		success : function(data) {
			let tags = data;
			let html = "";
			if (tags.length == 0) {
				html = "Nema pronađenih tagova...";
			} else {
				for (let i = 0; i < tags.length; i++) {
					html += "<input type='button' onclick='loadThumbnails(\""
							+ htmlEscape(tags[i]) + "\")' value='" + htmlEscape(tags[i]) + "'>";
				}
			}
			$("#tags").html(html);
		}
	});
}

/**
 * Loads the names of the images containing the given tag using REST and appends them
 * as clickable images to the HTML.
 * 
 * @param tag The given tag.
 */
function loadThumbnails(tag) {
	$.ajax({
		url : "rest/thumbs",
		data : {
			tag : tag,
			dummy : Math.random()
		},
		dataType : "json",
		success : function(data) {
			let thumbs = data;
			let html = "";
			for (let i = 0; i < thumbs.length; i++) {
				html += "<img src='servlets/thumbs?name=" + htmlEscape(thumbs[i])
						+ "' onclick='loadImage(\"" + htmlEscape(thumbs[i]) + "\")'>"
			}
			$("#thumbs").html(html);
			$("#image").html("");
		}
	});
}

/**
 * Loads the info for the image with the given name using REST and appends it
 * as text and image to the HTML.
 * 
 * @param name The given image name.
 */
function loadImage(name) {
	$.ajax({
		url : "rest/image",
		data : {
			name : name
		},
		dataType : "json",
		success : function(data) {
			let image = data;
			let html = "<p><b>Naziv:</b> " + htmlEscape(image.title) + "</p>";
			let tags = image.tags;
			html += "<p><b>Tagovi:</b>";
			let first = true;
			for (let i = 0; i < tags.length; i++) {
				if (first) {
					first = false;
				} else {
					html += ",";
				}
				html += " " + htmlEscape(tags[i]);
			}
			html += "</p>";
			html += "<img src='servlets/image?name=" + htmlEscape(image.name) + "'>";
			$("#image").html(html);
		}
	});
}

/**
 * Escapes HTML special symbols. Taken from StackOverflow.
 * 
 * @param str The given string.
 * @returns The appropriately escaped string.
 * @see http://stackoverflow.com/questions/1219860/html-encoding-in-javascript-jquery
 */
function htmlEscape(str) {
    return String(str)
            .replace(/&/g, '&amp;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;');
}