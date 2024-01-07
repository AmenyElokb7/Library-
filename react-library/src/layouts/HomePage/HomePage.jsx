import { Carousel } from "./components/Carousel";
import { ExploreTopBooks } from "./components/ExploreTopBooks";
import { Heros } from "./components/Heros";
import { LibraryServices } from "./components/LibraryServices";

export const HomePage = (props) => {
    return (
        <>
            <ExploreTopBooks/>
            <Carousel/>
            <Heros authUser={props.authUser}/>
            <LibraryServices authUser={props.authUser}/>
        </>
    );
}