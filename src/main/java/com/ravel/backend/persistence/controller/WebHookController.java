package com.ravel.backend.persistence.controller;

import com.ravel.backend.persistence.dto.*;
import com.ravel.backend.persistence.service.PersistenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "WebHooks Photon", description = "WebHooks for Persistence")
@RequestMapping(path = "webhook/photon")
@RestController
@AllArgsConstructor
public class WebHookController {

    private PersistenceService persistenceService;

    @Operation(
            summary = "Close",
            description = "PathClose, called when a room is removed from Photon servers memory. If IsPersistent is set to true the room state is sent."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Closed",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class)

                    ),
            }
    )
    @PostMapping(value = "/close", consumes = "application/json")
    public ResponseEntity<DefaultResponse> close(
            @RequestHeader(value = "API-KEY") String headerString,
            @RequestBody ClosePost closePost
    ) {
        return ResponseEntity.ok(persistenceService.handleOnClose(closePost));
    }


    @Operation(
            summary = "Create",
            description = "Path create, Called when a new room is created or when its state needs to be loaded from external service."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Created",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateResponse.class)
                    ),
            }
    )
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<CreateResponse> create(
            @RequestHeader(value = "API-KEY") String headerString,
            @RequestBody CreatePost createPost
    ) {
        return ResponseEntity.ok(persistenceService.handleOnCreate(createPost));
    }

    @Operation(
            summary = "Event",
            description = "Path even, Called when the client raises an event in the room with the web flag HttpForward set."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class)
                    ),
            }
    )
    @PostMapping(value = "/event", consumes = "application/json")
    public ResponseEntity<DefaultResponse> event(
            @RequestHeader(value = "API-KEY") String headerString,
            @RequestBody Object defaultResponse
    ) {
        DefaultResponse response = new DefaultResponse();
        response.setResultCode(0);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "GameProperties",
            description = "PathGameProperties, Called when the client sets a room or a player property with the web flag HttpForward set."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class)
                    ),
            }
    )
    @PostMapping(value = "/gameproperties", consumes = "application/json")
    public ResponseEntity<DefaultResponse> gameProperties(
            @RequestHeader(value = "API-KEY") String headerString,
            @RequestBody Object defaultResponse
    ) {
        DefaultResponse response = new DefaultResponse();
        response.setResultCode(0);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Join",
            description = "Path join, Called when a clients joins a room when it is in Photon servers memory."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class)
                    ),
            }
    )
    @PostMapping(value = "/join", consumes = "application/json")
    public ResponseEntity<DefaultResponse> join(
            @RequestHeader(value = "API-KEY") String headerString,
            @RequestBody JoinPost joinPost
    ) {
        return ResponseEntity.ok(persistenceService.handleOnJoin(joinPost));
    }

    @Operation(
            summary = "Leave",
            description = "Path leave, Called when a client leaves the room."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DefaultResponse.class)
                    ),
            }
    )
    @PostMapping(value = "/leave", consumes = "application/json")
    public ResponseEntity<DefaultResponse> leave(
            @RequestHeader(value = "API-KEY") String headerString,
            @RequestBody LeavePost leavePost
    ) {
        return ResponseEntity.ok(persistenceService.handleOnLeave(leavePost));
    }
}

